import org.json.JSONException;

public interface TargetStatusListener {

	public void OnTargetStatusUpdate(TargetState targetState) throws JSONException;
}
