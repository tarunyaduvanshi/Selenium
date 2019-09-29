package common;

import java.util.ArrayList;
import java.util.List;

public class UseCandidateDetails {


    public List<CandidateDetails> returncandidateDetaile(Integer n) {

        List<CandidateDetails> candidateDetailsList = new ArrayList<>();

        for (int i = 0; i <= n; i++) {
            CandidateDetails candidateDetails = new CandidateDetails();
            candidateDetails.setEmailID("tarun.yaduvanshi" + i + "gmail.com");
            candidateDetails.setName("tarun.yaduvanshi" + i);
            candidateDetailsList.add(candidateDetails);

        }


        return candidateDetailsList;
    }

}
