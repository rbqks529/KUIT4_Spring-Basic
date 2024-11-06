package kuit.springbasic.domain;


import java.sql.Date;
import java.time.LocalDate;

public class Question {
    private int questionId;
    private String writer;
    private String title;
    private String contents;
    private Date createdDate;
    private int countOfAnswer;

    public Question() {

    }

    public Question(int questionId, String writer, String title, String contents, Date createdDate, int countOfAnswer) {
        this.questionId = questionId;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.createdDate = createdDate;
        this.countOfAnswer = countOfAnswer;
    }

    public Question(String writer, String title, String contents, int countOfAnswer) {
        this.questionId = 0;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.createdDate = Date.valueOf(LocalDate.now());
        this.countOfAnswer = countOfAnswer;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public int getCountOfAnswer() {
        return countOfAnswer;
    }

    public void setCountOfAnswer(int countOfAnswer) {
        this.countOfAnswer = countOfAnswer;
    }

    public void updateTitleAndContents(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public void increaseCountOfAnswer() {
        countOfAnswer += 1;
    }

    public void decreaseCountOfAnswer() {
        countOfAnswer -= 1;
    }
    public boolean isSameUser(User user) {
        return writer.equals(user.getUserId());
    }

    public void updateCountofAnswer(Question question){
        this.countOfAnswer = question.countOfAnswer;
    }
    public void update(Question question) {
        this.questionId = question.questionId;
        this.writer = question.writer;
        this.title = question.title;
        this.contents = question.contents;
        this.createdDate = question.createdDate;
        this.countOfAnswer = question.countOfAnswer;
    }

    @Override
    public String toString() {
        return "Question{" +
                "questionId=" + questionId +
                ", writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", createdDate=" + createdDate +
                ", countOfAnswer=" + countOfAnswer +
                '}';
    }
}
