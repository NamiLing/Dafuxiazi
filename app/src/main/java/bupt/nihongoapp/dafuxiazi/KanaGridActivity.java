package bupt.nihongoapp.dafuxiazi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class KanaGridActivity extends AppCompatActivity {

    private String TAG = "Acivtity_KanaGrid";
    private String[] hiragana_qing;
    private String[] katagana_qing;
    private String[] hiragana_zhuo;
    private String[] katagana_zhuo;
    private String[] hiragana_niu;
    private String[] katagana_niu;

    private RecyclerView rcyc_kanagrid;
    private GridAdapter mAdapter;
    private int linespan = 6; //每行显示几个假名
    private int ping_pian = 0; //平假0片假1切换
    private int qing_zhuo_niu = 0; //清音0浊音1拗音切换2
    private TextView tv_kanaping;
    private TextView tv_kanapian;
    private TextView tv_qingyin;
    private TextView tv_zhuoyin;
    private TextView tv_niuyin;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kana_grid);

        initKanaData();
        mToolbar = findViewById(R.id.kanagrid_toolbar);
        tv_kanaping = findViewById(R.id.kanagrid_pingjia);
        tv_kanapian = findViewById(R.id.kanagrid_pianjia);
        tv_zhuoyin = findViewById(R.id.kanagrid_zhuoyin);
        tv_qingyin = findViewById(R.id.kanagrid_qingyin);
        tv_niuyin = findViewById(R.id.kanagrid_niuyin);
        rcyc_kanagrid = findViewById(R.id.kanagrid_recyclerview);
        rcyc_kanagrid.setLayoutManager(new GridLayoutManager(this, linespan));

        tv_kanaping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ping_pian != 0){
                    ping_pian = 0;
                    switch (qing_zhuo_niu){
                        case 0:
                            updateUI(hiragana_qing);// 平假名清音
                            break;
                        case 1:
                            updateUI(hiragana_zhuo);// 平假名浊音
                            break;
                        case 2:
                            updateUI(hiragana_niu);
                            break;
                    }
                }
            }
        });

        tv_kanapian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ping_pian != 1){
                    ping_pian = 1;
                    switch (qing_zhuo_niu){
                        case 0:
                            updateUI(katagana_qing);// 平假名清音
                            break;
                        case 1:
                            updateUI(katagana_zhuo);// 平假名浊音
                            break;
                        case 2:
                            updateUI(katagana_niu);
                            break;
                    }
                }
            }
        });

        tv_qingyin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (qing_zhuo_niu != 0){
                    if (qing_zhuo_niu == 2){
                        linespan = 6;
                        rcyc_kanagrid.setLayoutManager(new GridLayoutManager(KanaGridActivity.this, linespan));
                    }
                    qing_zhuo_niu = 0;
                    switch (ping_pian){
                        case 0:
                            updateUI(hiragana_qing);
                            break;
                        case 1:
                            updateUI(katagana_qing);
                            break;
                    }
                }
            }
        });

        tv_zhuoyin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (qing_zhuo_niu != 1){
                    if (qing_zhuo_niu == 2){
                        linespan = 6;
                        rcyc_kanagrid.setLayoutManager(new GridLayoutManager(KanaGridActivity.this, linespan));
                    }
                    qing_zhuo_niu = 1;
                    switch (ping_pian){
                        case 0:
                            updateUI(hiragana_zhuo);
                            break;
                        case 1:
                            updateUI(katagana_zhuo);
                            break;
                    }
                }
            }
        });

        tv_niuyin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (qing_zhuo_niu!=2){
                    linespan = 4;
                    rcyc_kanagrid.setLayoutManager(new GridLayoutManager(KanaGridActivity.this, linespan));
                    qing_zhuo_niu = 2;
                    switch (ping_pian){
                        case 0:
                            updateUI(hiragana_niu);
                            break;
                        case 1:
                            updateUI(katagana_niu);
                            break;
                    }
                }
            }
        });

        // default UI
        updateUI(hiragana_qing);

    }

    /**
     * set Adapter
     * @param kana
     */
    private void updateUI(String[] kana){
        mAdapter = new GridAdapter(this, kana);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // TODO: 5/11/18  补充监听事件，发音还是Start Activity
                if (position%linespan == linespan-1){
                    // Start new Activity
                    Intent intent = new Intent(KanaGridActivity.this, KanaStudyActivity.class);
                    intent.putExtra("qing_zhuo_niu", qing_zhuo_niu);
                    intent.putExtra("ping_pian", ping_pian);
                    String[] ping = new String[linespan-1];
                    String[] pian = new String[linespan-1];// 传入下一个界面的参数
                    int line = position/linespan;
                    switch (qing_zhuo_niu){
                        case 0: // 清音，传入十个数据
                            System.arraycopy(hiragana_qing, line*linespan, ping, 0, linespan-1);
                            System.arraycopy(katagana_qing, line*linespan, ping, 0, linespan-1);
                            break;
                        case 1:
                            System.arraycopy(hiragana_zhuo, line*linespan, ping, 0, linespan-1);
                            System.arraycopy(katagana_zhuo, line*linespan, ping, 0, linespan-1);
                            break;
                        case 3:
                            System.arraycopy(hiragana_niu, line*linespan, ping, 0, linespan-1);
                            System.arraycopy(katagana_niu, line*linespan, ping, 0, linespan-1);
                            break;
                    }
                    intent.putExtra("ping", ping);
                    intent.putExtra("pian", pian);
                    KanaGridActivity.this.startActivity(intent);

                }else {
                    Toast.makeText(KanaGridActivity.this, "发音", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Log.d(TAG, "一共有"+ mAdapter.getItemCount() +"项");
        Log.d(TAG, "linespan为"+ linespan + "\n 选项为"+ping_pian+qing_zhuo_niu);
        rcyc_kanagrid.setAdapter(mAdapter);
    }

    private void initKanaData(){
        hiragana_qing = new String[]{
                "あ", "い", "う", "え", "お", ">",
                "か",  "き", "く", "け", "こ", ">",
                "さ",  "し", "す", "せ", "そ", ">",
                "た",  "ち", "つ", "て", "と", ">",
                "な",  "に", "ぬ", "ね", "の", ">",
                "は",  "ひ", "ふ", "へ", "ほ", ">",
                "ま",  "み", "む", "め", "も", ">",
                "や",  "ぃ", "ゆ", "ぇ", "よ", ">",
                "ら",  "り", "る", "れ", "ろ", ">",
                "わ",  "ぃ", "う", "ぇ", "を", ">",
                "ん", " ", " ", " ", " ", ">"};
        katagana_qing = new String[]{
                "ア", "イ", "ウ", "エ", "オ", ">",
                "カ",  "キ", "ク", "ケ", "コ", ">",
                "サ",  "し", "ス", "セ", "ソ", ">",
                "タ",  "チ", "ツ", "テ", "ト",">",
                "ナ",  "ニ", "ヌ", "ネ", "ノ", ">",
                "ハ",  "ヒ", "フ", "ヘ", "ホ", ">",
                "マ",  "ミ", "ム", "メ", "モ", ">",
                "ヤ",  "ィ", "ユ", "ェ", "ヨ", ">",
                "ラ",  "リ", "ル", "レ", "ロ", ">",
                "ワ",  "ィ", "ウ", "ェ", "ヲ", ">",
                "ン", " ", " ", " ", " ", ">",
        };

        hiragana_zhuo = new String[]{
                "が", "ぎ", "ぐ", "げ", "ご", ">",
                "ざ",  "じ", "ず", "ぜ", "ゾ", ">",
                "だ",  "ぢ", "づ", "で", "ど", ">",
                "ば",  "び", "ぶ", "べ", "ぼ", ">",
        };

        katagana_zhuo = new String[]{
                "ガ", "ギ", "グ", "ゲ", "ゴ", ">",
                "ザ",  "ジ", "ズ", "ゼ", "ゾ", ">",
                "ダ",  "ヂ", "ヅ", "デ", "ド", ">",
                "バ",  "ビ", "ブ", "ベ", "ボ", ">",
        };

        hiragana_niu = new String[]{
                "きゃ", "きゅ", "きょ", ">",
                "ぎゃ", "ぎゅ", "ぎょ", ">",
                "しゃ", "しゅ", "しょ", ">",
                "ちゃ", "ちゅ", "ちょ", ">",
                "にゃ", "にゅ", "にょ", ">",
                "ひゃ", "ひゅ", "ひょ", ">",
                "びゃ", "びゅ", "びょ", ">",
                "ぴゃ", "ぴゅ", "ぴょ", ">",
                "みゃ", "みゅ", "みょ", ">",
                "りゃ", "りゅ", "りょ", ">"
        };

        katagana_niu = new String[]{
                "キャ", "キュ", "キョ", ">",
                "ギャ", "ギュ", "ギョ", ">",
                "シャ", "シュ", "ショ", ">",
                "チャ", "チュ", "チョ", ">",
                "ニャ", "ニュ", "ニョ", ">",
                "ヒャ", "ヒュ", "ヒョ", ">",
                "ビャ", "ビュ", "ビョ", ">",
                "ピャ", "ピュ", "ピョ", ">",
                "ミャ", "ミュ", "ミョ", ">",
                "リャ", "リュ", "リョ", ">"
        };
    }

    /**
     * 在Activity中设置item点击事件的方法第一步：
     * 第一步，定义接口,在activity里面使用setOnItemClickListener方法并创建此接口的对象、实现其方法
     */
    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

}
