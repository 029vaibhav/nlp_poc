import opennlp.tools.namefind.*;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.*;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class Trainer {
    static String onlpModelPath = "en-ner-person.bin";

    public static void main(String[] args) throws IOException {
        String paragraph = "Your Vodafone Alan McKennedy bill for Jan'17 of Rs.249.55 due on 30-01-2017 has been sent to your regd email ID vaibsy007@rediff.com. Pls check Inbox & if mail is not received, pls check your Spam/junk Box too. The Password to open your e-bill is vaib9078.";


        Charset charset = Charset.forName("UTF-8");
        TextAnalysis textAnalysis = new TextAnalysis();
        ObjectStream<String> lineStream = new PlainTextByLineStream(new MarkableFileInputStreamFactory(
                textAnalysis.getFile("en-ner-person.train")), charset);
        TokenNameFinderModel model = null;
        try (ObjectStream<NameSample> sampleStream = new NameSampleDataStream(
                lineStream)) {
            //         model = NameFinderME.train("en","drugs", sampleStream, Collections.<String,Object>emptyMap(),100,4) ;
            TokenNameFinderFactory tokenNameFinderFactory = new TokenNameFinderFactory();
            model = NameFinderME.train("en", "org", sampleStream, TrainingParameters.defaultParams(), tokenNameFinderFactory);
        }

        InputStream is = new FileInputStream(textAnalysis.getFile("da-token.bin"));
        TokenizerModel tokenizerModel = new TokenizerModel(is);
        Tokenizer tokenizer = new TokenizerME(tokenizerModel);
        String tokens[] = tokenizer.tokenize(paragraph);
        NameFinderME nameFinderME = new NameFinderME(model);
        Span[] spen = nameFinderME.find(tokens);
        List<String> strings = new ArrayList<>();

        for (Span span : spen) {
            strings.add(span.toString());
        }
        System.out.println(strings);







        BufferedOutputStream modelOut = null;
        try {
            modelOut = new BufferedOutputStream(new FileOutputStream(onlpModelPath));
            model.serialize(modelOut);
        } finally {
            if (modelOut != null)
                modelOut.close();
        }
    }
}
