package ga.cv3sarato.android.entity.response.mail;

import java.util.List;

public class MailEntity {

    private String id;
    private String snippet;
    private String subject;
    private long date;
    private List<Person> from;
    private List<Person> to;
    private List<Person> cc;
    private List<String> bcc;

    public class Person {
        public String email;
        public String name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public List<Person> getFrom() {
        return from;
    }

    public void setFrom(List<Person> from) {
        this.from = from;
    }

    public List<Person> getTo() {
        return to;
    }

    public void setTo(List<Person> to) {
        this.to = to;
    }

    public List<Person> getCc() {
        return cc;
    }

    public void setCc(List<Person> cc) {
        this.cc = cc;
    }

    public List<String> getBcc() {
        return bcc;
    }

    public void setBcc(List<String> bcc) {
        this.bcc = bcc;
    }
}
