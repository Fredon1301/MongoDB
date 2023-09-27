package unifacisa.com.MongoDB;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document; // Importe a classe Document do pacote org.bson
import java.io.IOException;

public class Extrair {

    public static void main(String[] args) {
        try {
            // Conecte-se ao banco de dados MongoDB (certifique-se de que o MongoDB esteja em execução)
            MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
            MongoDatabase database = mongoClient.getDatabase("Filmes"); // Substitua pelo nome do seu banco de dados
            MongoCollection<Document> collection = database.getCollection("filmes");

            org.jsoup.nodes.Document doc = Jsoup.connect("https://www.adorocinema.com/filmes/numero-cinemas/").get();
            Elements movieElements = doc.select(".mdl");

            for (Element movieElement : movieElements) {
                String titulo = movieElement.select(".meta-title").text();
                String ano = movieElement.select(".date").text();
                String rating = movieElement.select(".stareval-note").text();

                // Crie um documento BSON com os dados
                Document filme = new Document()
                    .append("Título", titulo)
                    .append("Ano", ano)
                    .append("Classificação", rating);

                // Insira o documento no MongoDB
                collection.insertOne(filme);
            }

            // Feche a conexão com o MongoDB
            mongoClient.close();

            System.out.println("Informacoes dos filmes inseridas no MongoDB");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
