package com.example.spinner;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.spinner.R.id.sp_city;
import static com.example.spinner.R.id.sp_county;

//三级联动下拉框
//当本级下拉框的选中值改变时，随之修改下级的适配器的绑定值

public class MainActivity extends Activity {

    private Spinner provinceSpinner;//省级或省级市
    private Spinner citySpinner;//地级市或者区
    private Spinner countySpinner;//县

    private Spinner sp_custom1;

    private ArrayAdapter<String> provinceAdapter;
    private ArrayAdapter<String> cityAdapter;
    private ArrayAdapter<String> countyAdapter;
    int provincePosition = 3;

    private String[] province = new String[]{"北京", "上海", "天津", "广东"};
    private String[][] city = new String[][]{
            {"东城区", "西城区", "崇文区", "宣武区", "朝阳区", "海淀区", "丰台区", "石景山区", "门头沟区",
                    "房山区", "通州区", "顺义区", "大兴区", "昌平区", "平谷区", "怀柔区", "密云县",
                    "延庆县"},

            {"长宁区", "静安区", "普陀区", "闸北区", "虹口区"},
            {"和平区", "河东区", "河西区", "南开区", "河北区", "红桥区", "塘沽区", "汉沽区", "大港区"},
            {"广州", "深圳", "韶关", "珠海"}
    };

    private String[][][] county = new String[][][]{
            {
                    {"无"}
                    //{"无", "无", "无", "无", "无", "无", "无", "无", "无", "无", "无", "无", "无", "无", "无", "无", "无", "无"}
            },

            {
                    //{"无", "无", "无", "无", "无"}
                    {"无"}
            },

            {
                    //{"无", "无", "无", "无", "无", "无", "无", "无", "无"}
                    {"无"}
            },
            {
                    //广东
                    {"海珠区", "荔湾区", "越秀区", "白云区", "萝岗区", "天河区", "黄埔区", "花都区", "从化市", "增城市", "番禺区", "南沙区"}, //广州
                    {"宝安区", "福田区", "龙岗区", "罗湖区", "南山区", "盐田区"}, //深圳
                    {"武江区", "浈江区", "曲江区", "乐昌市", "南雄市", "始兴县", "仁化县", "翁源县", "新丰县", "乳源县"},  //韶关
                    {"香洲区","金湾区","斗门区"}//珠海
            }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSpinner();
        initCustom();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initCustom() {
        sp_custom1 = (Spinner) findViewById(R.id.sp_custom1);
        final String[] data = new String[]{"账户管理","地址管理","柜机管理","资产管理"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,data);

        //自定义下拉菜单样式定义在/layout下.
        adapter.setDropDownViewResource(R.layout.spinner_custome1_item);
        sp_custom1.setAdapter(adapter);

    }

    private void setSpinner() {
        provinceSpinner = (Spinner) findViewById(R.id.sp_province);
        citySpinner = (Spinner) findViewById(sp_city);
        countySpinner = (Spinner) findViewById(sp_county);

        provinceAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,province);
        provinceAdapter.setDropDownViewResource(R.layout.drop_down_item);
        provinceSpinner.setAdapter(provinceAdapter);
        provinceSpinner.setSelection(3,true);

        cityAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,city[3]);
        citySpinner.setAdapter(cityAdapter);
        citySpinner.setSelection(0,true);

        countyAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,county[3][0]);
        countySpinner.setAdapter(countyAdapter);
        countySpinner.setSelection(0,true);

        //设置下拉监听事件
        provinceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cityAdapter = new ArrayAdapter<>(MainActivity.this,android.R.layout.simple_spinner_item,city[position]);
                citySpinner.setAdapter(cityAdapter);
                provincePosition = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //如果第一个是省级城市
                if(position >= county[provincePosition].length)
                    return;

                countyAdapter = new ArrayAdapter<>(MainActivity.this,android.R.layout.simple_spinner_item,county[provincePosition][position]);
                countySpinner.setAdapter(countyAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}
