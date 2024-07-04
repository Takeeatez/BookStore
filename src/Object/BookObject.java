package Object;

public class BookObject {

    private String bookId;
    private String bookName;
    private String category; //프로그래밍 언어 등
    private int price;
    private String date;
    private String writer;
    private String description;
    private int stock; // 재고

    // 생성자
    public BookObject(String bookId, String category, int price, String bookName, String date, String writer, String description, int stock) {
        this.bookId = bookId;
        this.category = category;
        this.price = price;
        this.bookName = bookName;
        this.date = date;
        this.writer = writer;
        this.description = description;
        this.stock = stock;
    }

    // getter 및 setter 메서드
    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return String.format("%s,%s,%d,%s,%s,%s,%s,%d",
                bookId, category, price, bookName, date, writer, description, stock);
    }
}
