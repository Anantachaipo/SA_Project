package ku.cs.model;

public class CommentLawsuits {
    private Integer c_id;
    private String c_lawsuitsComment;
    private Integer ls_id;


    public CommentLawsuits(Integer c_id, String c_lawsuitsComment, Integer ls_id) {
        this.c_id = c_id;
        this.c_lawsuitsComment = c_lawsuitsComment;
        this.ls_id = ls_id;
    }

    public Integer getC_id() {
        return c_id;
    }

    public void setC_id(Integer c_id) {
        this.c_id = c_id;
    }

    public String getC_lawsuitsComment() {
        return c_lawsuitsComment;
    }

    public void setC_lawsuitsComment(String c_lawsuitsComment) {
        this.c_lawsuitsComment = c_lawsuitsComment;
    }

    public Integer getLs_id() {
        return ls_id;
    }

    public void setLs_id(Integer ls_id) {
        this.ls_id = ls_id;
    }


}
