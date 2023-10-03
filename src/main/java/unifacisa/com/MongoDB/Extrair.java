package unifacisa.com.MongoDB;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document; 
import java.io.IOException;

public class Extrair {

    public static void main(String[] args) {
        try {
            
            MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
            MongoDatabase database = mongoClient.getDatabase("Filmes"); 
            MongoCollection<Document> collection = database.getCollection("filmes");

            org.jsoup.nodes.Document doc = Jsoup.connect("https://www.adorocinema.com/filmes/numero-cinemas/").get();
            Elements movieElements = doc.select(".mdl");

            for (Element movieElement : movieElements) {
                String titulo = movieElement.select(".meta-title").text();
                String ano = movieElement.select(".date").text();
                String rating = movieElement.select(".stareval-note").text();

             
                Document filme = new Document()
                    .append("Título", titulo)
                    .append("Ano", ano)
                    .append("Classificação", rating);

     
                collection.insertOne(filme);
            }

            mongoClient.close();

            System.out.println("Informacoes dos filmes inseridas no MongoDB");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
