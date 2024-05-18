package hello.dto;

public class AddBoardDto implements BoardDto{
    private String title;
    private String content;

    public AddBoardDto(String title, String content) {
        this.title = title;
        this.content = content;
    }


    @Override
    public String getContent() {
        return content;
    }

    @Override
    public String getTitle() {
        return title;
    }

}
