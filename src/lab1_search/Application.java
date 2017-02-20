package lab1_search;

public class Application {
    public static void main(String[] args) {
        ThreadForm threadForm = new ThreadForm();
        SearchThread thread1 = new SearchThread("1ый", new SearchInfo("/Users/artnest/", "*.txt",
                "Thread", true, true, false));

        SearchThread thread2 = new SearchThread("2ой", new SearchInfo("/Users/artnest/", "*.txt",
                "Thread", true, true, false));
        thread1.start();
        thread2.start();
    }
}
