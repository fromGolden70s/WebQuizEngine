package engine.service;


public class Reply {
    final String CORRECT = "Congratulations, you're right!";
    final String WRONG = "Wrong answer! Please, try again.";
    private boolean success;
    private String feedback;

    public Reply() {
    }
    public Reply(boolean success, String feedback) {
        this.success = success;
        this.feedback = feedback;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getFeedback() {
        return feedback;
    }
}
