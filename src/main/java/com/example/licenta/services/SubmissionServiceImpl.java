package com.example.licenta.services;

import com.example.licenta.builders.SubmissionBuilder;
import com.example.licenta.configs.RabbitMQConfig;
import com.example.licenta.dtos.submissions.SubmissionRequest;
import com.example.licenta.dtos.user.security.LocalUser;
import com.example.licenta.entities.Problem;
import com.example.licenta.entities.Submission;
import com.example.licenta.exceptions.ResourceNotFoundException;
import com.example.licenta.services.interfaces.ISubmissionService;
import com.example.licenta.services.repositories.ProblemRepository;
import com.example.licenta.services.repositories.SubmissionRepository;
import com.example.licenta.services.repositories.UserRepository;
import com.example.licenta.entities.User;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SubmissionServiceImpl implements ISubmissionService {

    @Autowired
    private SubmissionRepository submissionRepository;

    @Autowired
    private ProblemRepository problemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private StorageService storageService;

    @Override
    public List<SubmissionRequest> getAllSubmissions() {
        List<Submission> submissions = submissionRepository.findAll();
        return submissions.stream()
                .map(SubmissionBuilder::generateSubmissionRequest)
                .collect(Collectors.toList());
    }




    private File createFileFromSolution(String submissionId, String solution){
        try {
            FileWriter myWriter = new FileWriter(submissionId);
            myWriter.write(solution);
            myWriter.close();
            return new File(submissionId);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Submission addNewSubmission(SubmissionRequest submissionRequest, Authentication authentication) {
        LocalUser authUser = (LocalUser) authentication.getPrincipal();
        Optional<User> user = userRepository.findById(authUser.getUser().getId());
        if (user.isEmpty()){
            throw new ResourceNotFoundException("User", "id", authUser.getUser().getId());
        }
        Optional<Problem> problem = problemRepository.findById(submissionRequest.getProblemID());
        if (problem.isEmpty()){
            throw new ResourceNotFoundException("Problem", "id", submissionRequest.getProblemID());
        }

        Submission submission = SubmissionBuilder.generateSubmission(submissionRequest, user.get(), problem.get());
        submission = submissionRepository.save(submission);
        rabbitTemplate.convertAndSend(RabbitMQConfig.TOPIC_EXCHANGE_NAME,RabbitMQConfig.ROUTING_KEY_NAME,submission.getId());

        File file = createFileFromSolution(submission.getId().toString(), submissionRequest.getSolutionText());
        storageService.uploadFile(problem.get().getTitle(),file);
        file.delete();

        return submission;
    }

    @Override
    public SubmissionRequest getSubmissionById(UUID id) {
        Optional<Submission> submission = submissionRepository.findById(id);
        if (submission.isEmpty()){
            throw new ResourceNotFoundException("Submission", "id", id);
        }
        return SubmissionBuilder.generateSubmissionRequest(submission.get());

    }
}
