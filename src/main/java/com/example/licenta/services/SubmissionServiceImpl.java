package com.example.licenta.services;

import com.example.licenta.builders.SubmissionBuilder;
import com.example.licenta.configs.rabbitMQ.RabbitMQConfig;
import com.example.licenta.dtos.submissions.SubmissionDetails;
import com.example.licenta.dtos.user.security.LocalUser;
import com.example.licenta.entities.Problem;
import com.example.licenta.entities.Submission;
import com.example.licenta.exceptions.ResourceNotFoundException;
import com.example.licenta.services.repositories.ProblemRepository;
import com.example.licenta.services.repositories.SubmissionRepository;
import com.example.licenta.services.repositories.UserRepository;
import com.example.licenta.entities.User;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SubmissionServiceImpl implements ISubmissionService{

    @Autowired
    private SubmissionRepository submissionRepository;

    @Autowired
    private ProblemRepository problemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public List<SubmissionDetails> getAllSubmissions() {
        List<Submission> submissions = submissionRepository.findAll();
        return submissions.stream()
                .map(SubmissionBuilder::generateSubmissionDetails)
                .collect(Collectors.toList());
    }

    @Override
    public Submission addNewSubmission(SubmissionDetails submissionDetails, Authentication authentication) {
        LocalUser authUser = (LocalUser) authentication.getPrincipal();
        Optional<User> user = userRepository.findById(authUser.getUser().getId());
        if (user.isEmpty()){
            throw new ResourceNotFoundException("User", "id", authUser.getUser().getId());
        }
        Optional<Problem> problem = problemRepository.findById(submissionDetails.getProblemID());
        if (problem.isEmpty()){
            throw new ResourceNotFoundException("Problem", "id", submissionDetails.getProblemID());
        }

        Submission submission = SubmissionBuilder.generateSubmission(submissionDetails, user.get(), problem.get());
        submission = submissionRepository.save(submission);
        rabbitTemplate.convertAndSend(RabbitMQConfig.TOPIC_EXCHANGE_NAME,RabbitMQConfig.ROUTING_KEY_NAME,submission.getId());
        return submission;
    }
}
