package com.hoperun.chorny.customlistview.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hoperun.chorny.customlistview.CONSTANTS;
import com.hoperun.chorny.customlistview.R;
import com.hoperun.chorny.customlistview.adapter.CommonAdapter;
import com.hoperun.chorny.customlistview.adapter.viewhodler.ViewHolder;
import com.hoperun.chorny.customlistview.entity.ItemEntity;
import com.hoperun.chorny.customlistview.entity.ResultEntity;
import com.hoperun.chorny.customlistview.utils.WebUtils;
import com.hoperun.chorny.customlistview.view.XListView;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONObject;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * created by xiaoyu.zhang at 2017/2/25
 */
public class MainActivity extends Activity implements XListView.IXListViewListener {
    private List<ItemEntity> data_item = new ArrayList<ItemEntity>();
    private List<ItemEntity> data_current = new ArrayList<ItemEntity>();
    private XListView lv_news;
    private TextView tv_tittle;
    private CommonAdapter adapter;
    int dataIndex = 0;
    int pageSize = 5;
    int maxPage = 3;
    int pageIndex = 1;
    Handler handler_updateUI = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            if( msg.arg1 == 100 ){
                String tittle = (String) msg.obj;
                tv_tittle.setText(tittle);
                upDateData();
            }

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        LoadData();
    }

    private void init() {
        lv_news = (XListView) findViewById(R.id.lv_news);
        tv_tittle = (TextView) findViewById(R.id.tv_tittle);
        lv_news.setPullLoadEnable(true);
        lv_news.setXListViewListener(MainActivity.this);
    }

    private void LoadData(){
        new Thread(){
            @Override
            public void run() {
                try {
                    byte[] result = WebUtils.sendHttpGet(CONSTANTS.HOST,null);
                    String json = null;
                    json = new String(result, "utf-8");
                    Log.i("xiaoyu", "json=" + json);

                    JSONObject obj = new JSONObject(json);
                    String bTittle = obj.getString("title");
                    Log.i("xiaoyu", "bTittle = "+bTittle);
                    JSONArray array = obj.getJSONArray("rows");
                    ItemEntity ie = null;
                    for( int i = 0;i<array.length();i++ ){
                        JSONObject sjobj = array.getJSONObject(i);
                        String title = sjobj.getString("title");
                        String description = sjobj.getString("description");
                        String imageHref = sjobj.getString("imageHref");
                        Log.i("xiaoyu", "title = "+title+",description = "+description+"imageHref"+imageHref);
                        ie = new ItemEntity(title,description,imageHref);
                        data_item.add(ie);
                    }
                    Message message = Message.obtain();
                    message.obj = bTittle;
                    message.arg1 = 100;
                    handler_updateUI.sendMessage(message);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private List<ItemEntity> getCurrentPageData() {
        if( pageIndex<maxPage ) {
            for (int i = dataIndex; i < pageSize; i++) {
                data_current.add(data_item.get(i));
            }
        }else if(pageIndex == maxPage){
            for (int i = 0; i < 4; i++) {
                data_current.add(data_item.get(i));
            }
        }else{
            Toast.makeText(getApplicationContext(),"没有更多数据了!",Toast.LENGTH_SHORT);
        }
        return data_current;
    }

    private void upDateData(){
        lv_news.setAdapter(adapter = new CommonAdapter<ItemEntity>(
                getApplicationContext(), getCurrentPageData(),
                R.layout.item_news) {
            @Override
            public void convert(ViewHolder helper, ItemEntity item) {
                TextView tv_bTittile = helper.getView(R.id.tv_btittle);
                tv_bTittile.setText(item.getTittle());
                TextView tv_sTittile = helper.getView(R.id.tv_stittle);
                tv_sTittile.setText(item.getDescription());
                ImageView iv_icon = helper.getView(R.id.iv_icon);
                ImageLoader.getInstance().displayImage(item.getImageHref(), iv_icon);
            }
        });
    }

    private void onLoad() {
        lv_news.stopRefresh();
        lv_news.stopLoadMore();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowTime = df.format(new Date(System.currentTimeMillis()));
        lv_news.setRefreshTime(nowTime);
    }

    @Override
    public void onRefresh() {
        data_current.clear();
        getCurrentPageData();
        upDateData();
        adapter.notifyDataSetChanged();
        onLoad();
    }

    @Override
    public void onLoadMore() {
        dataIndex = dataIndex + 5;
        pageIndex++;
        getCurrentPageData();
        upDateData();
        adapter.notifyDataSetChanged();
        onLoad();
    }
}
