public class BaseTarget {
    protected String accessKey;
    protected String secretKey;

    //    private String targetId = "[ target id ]";
    protected String url;

    protected String targetName;
    protected String imageLocation;

    BaseTarget(){
        accessKey = "d3f0e6fb85a3c172f4ef6f4aa45f33d5643a6d04";
        secretKey = "20577970fa5c6cff194e38ea8ec0d60f79965859";

        //    private String targetId = "[ target id ]";
        url = "https://vws.vuforia.com";

        targetName = "cylinder";
        imageLocation = System.getProperty("user.dir")+"/cylinder.jpg";
    }
}
