import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Span;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class TextAnalysis {


    public static void main(String[] args) throws IOException {

        String paragraph = "Hi Alan McKennedy bill for Jan'17 of Rs.249.55 due on 30-01-2017 has been sent to your regd email ID vaibsy007@rediff.com. Pls check Inbox & if mail is not received, pls check your Spam/junk Box too. The Password to open your e-bill is vaib9078.";

        TextAnalysis textAnalysis = new TextAnalysis();
        InputStream is = new FileInputStream(textAnalysis.getFile("da-token.bin"));
        TokenizerModel tokenizerModel = new TokenizerModel(is);
        Tokenizer tokenizer = new TokenizerME(tokenizerModel);
        String tokens[] = tokenizer.tokenize(paragraph);
        is = new FileInputStream(textAnalysis.getFile("en-ner-person.bin"));

        TokenNameFinderModel model = new TokenNameFinderModel(is);
        NameFinderME nameFinderME = new NameFinderME(model);
        Span[] spen = nameFinderME.find(tokens);
        List<String> strings = new ArrayList<String>();
        for (Span span : spen) {
            strings.add(span.toString());
        }
        System.out.println(strings);


        is = new FileInputStream(textAnalysis.getFile("en-ner-money.bin"));

        model = new TokenNameFinderModel(is);
        nameFinderME = new NameFinderME(model);
        spen = nameFinderME.find(tokens);
        strings = new ArrayList<String>();
        for (Span span : spen) {
            strings.add(span.toString());
        }
        System.out.println(strings);

        is = new FileInputStream(textAnalysis.getFile("en-ner-date.bin"));
        model = new TokenNameFinderModel(is);
        nameFinderME = new NameFinderME(model);
        spen = nameFinderME.find(tokens);
        strings = new ArrayList<String>();
        for (Span span : spen) {
            strings.add(span.toString());
        }
        System.out.println(strings);
        is = new FileInputStream(textAnalysis.getFile("en-ner-person.bin"));
        model = new TokenNameFinderModel(is);
        nameFinderME = new NameFinderME(model);
        spen = nameFinderME.find(tokens);
        strings = new ArrayList<String>();
        for (Span span : spen) {
            strings.add(span.toString());
        }
        System.out.println(strings);
        is = new FileInputStream(textAnalysis.getFile("en-ner-time.bin"));
        model = new TokenNameFinderModel(is);
        nameFinderME = new NameFinderME(model);
        spen = nameFinderME.find(tokens);
        strings = new ArrayList<String>();
        for (Span span : spen) {
            strings.add(span.toString());
        }
        System.out.println(strings);

        is.close();

    }

    public File getFile(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        return new File(classLoader.getResource(fileName).getFile());
    }
}
