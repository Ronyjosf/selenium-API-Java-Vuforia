import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

public class TestAPI {


    @BeforeClass
    public static void before () throws IOException, URISyntaxException, JSONException {

    }

    @Test
    public void deleteAllTargets() throws IOException, URISyntaxException, JSONException {
        // get all existing targets
        GetAllTargets allTargets = new GetAllTargets();
        JSONArray targetIds = allTargets.getTargets();

        for (int i=0; i< targetIds.length(); i++) {
            String targetId = targetIds.get(i).toString();

            // delete & deactivate
            DeleteTarget d = new DeleteTarget(targetId);
            d.deleteTarget(); // deleteTarget method handles the Exception
            System.out.print("finish deleting target: "+targetId);
            d.deactivateThenDeleteTarget();
            System.out.print("finish deactivating target: "+targetId);
        }
    }
    @Test
    public void addNewTarget() throws Exception {
        Target target = new Target();
        String targetId = target.addTarget();
        if (targetId == null || targetId.toString().isEmpty())
            Assert.assertTrue("target not created", false);
        System.out.print("targetId is: "+targetId);

    }
}
