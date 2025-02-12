package netflix;
import java.util.*;

class Movie {
	String title;
    String genre;
    int watchTime;
    public Movie(String title, String genre, int watchTime) {
        this.title = title;
        this.genre = genre;
        this.watchTime = watchTime;
    }
}

class User {
	
    String name;
    
    List<Movie> watchedMovies = new ArrayList<>();
    
    public User(String name) {
        this.name = name;
    }
    public void watchMovie(Movie movie) {
    	
        watchedMovies.add(movie);
        
        System.out.println("Currently Watching " + movie.title);   
    }
    
    public void recommendedMovie(List<Movie> movies) {
    	
        Map<String, Integer> genreCount = new HashMap<>();
        
        for (Movie movie : watchedMovies) {
            genreCount.put(movie.genre, genreCount.getOrDefault(movie.genre, 0) + movie.watchTime);
        }
        
        String favoriteGenre = Collections.max(genreCount.entrySet(), Map.Entry.comparingByValue()).getKey();
        
        System.out.println("Most Watched Genre " + favoriteGenre);
        
        System.out.println("Recommended Movies");
        
        for (Movie movie : movies) {
            if (movie.genre.equals(favoriteGenre) && !watchedMovies.contains(movie)) {
                System.out.println("- " + movie.title);
            }
        }
    }
}

public class netflix {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        //dani iadd new movies
        List<Movie> allMovies = Arrays.asList(
            new Movie("War of Worlds", "Sci-Fi", 120),
            new Movie("Shes Dating The Gangster", "Romance", 180),
            new Movie("Avengers Endgame", "Action", 140),
            new Movie("Interstellar", "Sci-Fi", 150)
        );
        
        System.out.print("Enter Your Name " );
        User user = new User(scanner.nextLine());
        
        while (true) {
            System.out.println("\n1. Watch a movie\n2. Get recommendations\n3. Exit");
            System.out.print("Choose an option 1-3: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            if (choice == 1) {
                System.out.println("Choose a Movie");
                for (int i = 0; i < allMovies.size(); i++) {
                    System.out.println((i + 1) + ". " + allMovies.get(i).title + " (" + allMovies.get(i).genre + ")");
                }
                System.out.print("Enter the movie to watch ");
                int movieIndex = scanner.nextInt() - 1;
                scanner.nextLine();
                if (movieIndex >= 0 && movieIndex < allMovies.size()) {
                    user.watchMovie(allMovies.get(movieIndex));
                } else {
                    System.out.println("Invalid selection.");
                }
            } else if (choice == 2) {
                if (user.watchedMovies.isEmpty()) {
                    System.out.println("No movies Watched");
                } else {
                    user.recommendedMovie(allMovies);
                }
            } else if (choice == 3) {
                System.out.println("See you again");
                break;
            } else {
                System.out.println("Invalid");
            }
        }
        scanner.close();
    }
}
