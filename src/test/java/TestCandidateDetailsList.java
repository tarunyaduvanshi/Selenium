import common.CandidateDetails;
import common.UseCandidateDetails;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class TestCandidateDetailsList {

    @Test
    public void verifyCandidateSize(){
        UseCandidateDetails useCandidateDetails=new UseCandidateDetails();
        List<CandidateDetails> candidateDetailsList = useCandidateDetails.returncandidateDetaile(10);
        for (CandidateDetails candidateDetails:candidateDetailsList) {
            System.out.println(candidateDetails.getEmailID());
            System.out.println(candidateDetails.getName());

        }
        Assert.assertEquals(10,candidateDetailsList.size());

    }
}
