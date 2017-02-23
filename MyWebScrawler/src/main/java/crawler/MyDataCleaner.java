package crawler;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.LetterTokenizer;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.util.Version;

import java.io.Reader;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by merlin on 17/2/15.
 */
public class MyDataCleaner extends Analyzer{

    private Set stops;

    public MyDataCleaner(String[] sws) {

        //将字符串数组添加到停用词的set集合中
        stops = StopFilter.makeStopSet(Version.LUCENE_46, sws, true);
        //加入原来的停用词
        stops.addAll(StopAnalyzer.ENGLISH_STOP_WORDS_SET);
    }

    public MyDataCleaner() {
        stops = new HashSet<>();
        stops.addAll(StopAnalyzer.ENGLISH_STOP_WORDS_SET);//加入原来的停用词
    }

    @Override
    protected TokenStreamComponents createComponents(String fieldName, Reader reader) {
        //主要负责接收reader,将reader进行分词操作
        Tokenizer tokenizer = new LetterTokenizer(Version.LUCENE_46, reader);
        //创建停用词的set对象
        CharArraySet charArraySet = CharArraySet.copy(Version.LUCENE_46, stops);
        //分词器做好处理之后得到的一个流，这个流中存储了分词的信息
        //使用了忽略大小写的filter,停用filter过滤
        TokenStream tokenStream = new LowerCaseFilter(Version.LUCENE_46, new StopFilter(Version.LUCENE_46, tokenizer, charArraySet));
        return new TokenStreamComponents(tokenizer, tokenStream);
    }

}
