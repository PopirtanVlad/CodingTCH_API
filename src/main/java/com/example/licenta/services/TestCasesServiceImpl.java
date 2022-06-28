package com.example.licenta.services;

import com.example.licenta.entities.Problem;
import com.example.licenta.entities.TestCase;
import com.example.licenta.exceptions.BadRequestException;
import com.example.licenta.exceptions.ResourceNotFoundException;
import com.example.licenta.services.interfaces.ITestCaseService;
import com.example.licenta.services.repositories.TestCasesRepository;
import com.example.licenta.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

@Service
public class TestCasesServiceImpl implements ITestCaseService {

    @Autowired
    private TestCasesRepository testCasesRepository;

    @Autowired
    private ProblemServiceImpl problemService;

    @Autowired
    private StorageService storageService;

    public String saveTestCases(MultipartFile[] files){
        Arrays.asList(files).stream().forEach(multipartFile -> {

            try {
                Problem problem = problemService.findProblemByTitle(multipartFile.getOriginalFilename().split("_")[0]);
                String fileName = multipartFile.getOriginalFilename().split("_")[1];

                TestCase testCase = new TestCase();
                if (fileName.contains(".in")) {
                    testCase.setInputFilePath(fileName);
                    testCase.setExpectedFilePath(fileName.split("\\.")[0] + ".ref");
                    testCase.setProblem(problem);
                    testCasesRepository.save(testCase);
                }

                File file = new File(fileName);

                OutputStream os = new FileOutputStream(file);
                os.write(multipartFile.getBytes());
                os.close();
                storageService.uploadFile(problem.getTitle(), file);
                file.delete();

            } catch (IOException | ResourceNotFoundException e) {
                problemService.deleteProblemByTitle(multipartFile.getOriginalFilename().split("_")[0]);
                e.printStackTrace();
                throw new BadRequestException("Problem couldn't be saved because of bad test cases upload");
            }
        });
        return "Test cases saved";
    }

}
