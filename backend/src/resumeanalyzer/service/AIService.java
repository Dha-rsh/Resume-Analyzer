package resumeanalyzer.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.google.gson.*;

import resumeanalyzer.model.ResumeResult;

public class AIService {

   
    private static final String API_KEY = "AIzaSyDDAUa7GZ6HuJ6JMr20u3H2TSw9qhP4Ijc";

    public String analyze(String txt,String jobDesc) {
        try {
            
            txt = txt.replace("\"", "\\\"").replace("\n", " "); 
String prompt = 
"Analyze the following resume based on job description "+jobDesc+" and return ONLY valid JSON.\n\n" +

"Rules:\n" +
"- atsScore: 0 to 100\n" +
"- skills: max 5\n" +
"- missingSkills: max 5\n" +
"- matchScore: based on job description \n" +
"- sections: true/false\n" +
"- suggestions: max 3 short points\n" +
"- NO explanation\n" +
"- NO extra text\n\n" +

"Return ONLY this format:\n" +
"{\n" +
"  \"atsScore\": number,\n" +
"  \"skills\": [],\n" +
"  \"missingSkills\": [],\n" +
"  \"matchScore\": number,\n" +
"  \"sections\": {\n" +
"    \"education\": true,\n" +
"    \"projects\": true,\n" +
"    \"experience\": true\n" +
"  },\n" +
"  \"suggestions\": []\n" +
"}\n\n" +

"Resume:\n" + txt;
         
           String endpoint =
"https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=" + API_KEY;
          
            JsonObject part = new JsonObject();
            part.addProperty("text", prompt);

            JsonArray parts = new JsonArray();
            parts.add(part);

            JsonObject content = new JsonObject();
            content.add("parts", parts);

            JsonArray contents = new JsonArray();
            contents.add(content);

            JsonObject requestJson = new JsonObject();
            requestJson.add("contents", contents);

            String requestBody = requestJson.toString();

          
            System.out.println("Endpoint: " + endpoint);

            
            HttpClient client = HttpClient.newBuilder()
                    .version(HttpClient.Version.HTTP_1_1)
                    .build();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(endpoint.trim()))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

          
            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("API RESPONSE: " + response.body());

            JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();

          
            if (json.has("error")) {
                return "{ \"error\": \"" +
                        json.getAsJsonObject("error").get("message").getAsString() +
                        "\" }";
            }

            if (!json.has("candidates")) {
                return "{ \"error\": \"Invalid API response\" }";
            }

          
            String text = json
                    .getAsJsonArray("candidates")
                    .get(0).getAsJsonObject()
                    .getAsJsonObject("content")
                    .getAsJsonArray("parts")
                    .get(0).getAsJsonObject()
                    .get("text").getAsString();

           
            text = text.replace("```json", "")
                       .replace("```", "")
                       .trim();

            System.out.println("CLEANED TEXT: " + text);

            Gson gson = new Gson();
            ResumeResult result = gson.fromJson(text, ResumeResult.class);

            System.out.println("PARSED RESULT: " + result);

            return gson.toJson(result);

        } catch (Exception e) {
            e.printStackTrace();
            return "{ \"error\": \"Failed to analyze resume\" }";
        }
    }
}