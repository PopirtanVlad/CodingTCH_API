package com.example.licenta.services;

import com.example.licenta.builders.SubmissionBuilder;
import com.example.licenta.builders.TestResultBuilder;
import com.example.licenta.configs.RabbitMQConfig;
import com.example.licenta.dtos.TestResultInfo;
import com.example.licenta.dtos.submissions.SubmissionDetails;
import com.example.licenta.dtos.submissions.SubmissionPreview;
import com.example.licenta.dtos.submissions.SubmissionRequest;
import com.example.licenta.dtos.user.security.LocalUser;
import com.example.licenta.entities.Problem;
import com.example.licenta.entities.Submission;
import com.example.licenta.entities.TestResult;
import com.example.licenta.exceptions.ResourceNotFoundException;
import com.example.licenta.services.interfaces.ISubmissionService;
import com.example.licenta.services.repositories.ProblemRepository;
import com.example.licenta.services.repositories.SubmissionRepository;
import com.example.licenta.services.repositories.TestResultsRepo;
import com.example.licenta.services.repositories.UserRepository;
import com.example.licenta.entities.User;

import com.example.licenta.utils.FileUtils;
import com.example.licenta.utils.SubmissionStatus;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
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

    @Autowired
    private TestResultsRepo testResultsRepo;

    @Override
    public List<SubmissionPreview> getAllSubmissionsByUser(Authentication authentication) {

        LocalUser authUser = (LocalUser) authentication.getPrincipal();
        Optional<User> user = userRepository.findById(authUser.getUser().getId());
        if (user.isEmpty()){
            throw new ResourceNotFoundException("User", "id", authUser.getUser().getId());
        }

        List<Submission> submissions = submissionRepository.findByUser(user.get());
        return submissions.stream()
                .map(SubmissionBuilder::generateSubmissionPreview)
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

        submission.setSubmissionStatus(SubmissionStatus.IN_PROGRESS);
        submission.setUploadTime(LocalDateTime.now());
        submission = submissionRepository.save(submission);


        File file = createFileFromSolution(submission.getId().toString(), submissionRequest.getSolutionText());
        storageService.uploadFile(problem.get().getTitle(),file);


        rabbitTemplate.convertAndSend(RabbitMQConfig.TOPIC_EXCHANGE_NAME,RabbitMQConfig.ROUTING_KEY_NAME,submission.getId());

        file.delete();

        return submission;
    }

    @Override
    public SubmissionDetails getSubmissionDetailsById(UUID id) {
        Optional<Submission> submission = submissionRepository.findById(id);
        if (submission.isEmpty()){
            throw new ResourceNotFoundException("Submission", "id", id);
        }
        String solution = storageService.downloadFile(submission.get().getProblem().getTitle(), id.toString());

        List<TestResult> testResults = testResultsRepo.findBySubmission(submission.get());

        List<TestResultInfo> testResultsInfos = testResults.stream()
                .map(TestResultBuilder::generateTestResultInfo)
                .collect(Collectors.toList());

        SubmissionDetails submissionDetails = SubmissionBuilder.generateSubmissionDetails(submission.get(), submission.get().getProblem(),solution, testResultsInfos);
        return submissionDetails;
    }
}
