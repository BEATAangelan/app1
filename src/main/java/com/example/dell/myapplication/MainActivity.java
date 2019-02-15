package com.example.dell.myapplication;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bwie.greendao_type1.db.DaoMaster;
import com.bwie.greendao_type1.db.DaoSession;
import com.bwie.greendao_type1.db.UserDao;
import com.example.dell.myapplication.adapter.SearchAdapter;
import com.example.dell.myapplication.bean.Query;
import com.example.dell.myapplication.dao.User;
import com.example.dell.myapplication.presenter.IPersentermpl;
import com.example.dell.myapplication.utils.NetWorkUtils;
import com.example.dell.myapplication.view.IView;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Result;

public class MainActivity extends AppCompatActivity implements IView{
    private SearchAdapter searchAdapter;
   /* private String path="commodity/v1/findCommodityByKeyword?page=1&count=10&keyword=电脑";*/
    private IPersentermpl iPersentermpl;
    private RecyclerView recyclerView;
    private Button button;
    private EditText editText;
    private DaoSession daoSession;
    private List<Query.ResultBean> result;
    private String name1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iPersentermpl=new IPersentermpl(this);
        recyclerView = findViewById(R.id.recyc);
        editText=findViewById(R.id.serch);
       // String search = editText.getText().toString();
        initGreenDao();
        //name1 = editText.getText().toString();
       // Toast.makeText(MainActivity.this,name,Toast.LENGTH_SHORT).show();
        findViewById(R.id.button_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name1 = editText.getText().toString();
                Toast.makeText(MainActivity.this,name1,Toast.LENGTH_SHORT).show();
                String path="commodity/v1/findCommodityByKeyword?page=1&count=10&keyword=%s";
                DaoSession daoSession = getDaoSession();
                // Toast.makeText(MainActivity.this, "点击搜索",Toast.LENGTH_SHORT).show();
                iPersentermpl.getRequest(String.format(path, name1), Query.class);
                GridLayoutManager layoutManager = new GridLayoutManager(MainActivity.this,2);
                layoutManager.setOrientation(OrientationHelper.VERTICAL);
                searchAdapter = new SearchAdapter(MainActivity.this);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(searchAdapter);
            }
        });
    }
    @Override
    public void onSuccess(Object data) {
     Query bean= (Query) data;
     if(NetWorkUtils.isNetWork(this)) {
         result = bean.getResult();
         searchAdapter.setList(result);
         for (int i = 0; i < result.size(); i++) {
             String title = result.get(i).getCommodityName();
             String masterPic = result.get(i).getMasterPic();
             User user = new User();
             user.setTitle(title);
             user.setImg(masterPic);
             daoSession.insert(user);
      }
     }
     else{
         List<User> user = daoSession.loadAll(User.class);
         for(User user1:user){
             Long id = user1.getId();
             String title = user1.getTitle();
             String img = user1.getImg();
             //searchAdapter.setList(user1);
         }
         searchAdapter.notifyDataSetChanged();
//        // return user;
      }
    }

    private void initGreenDao() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "aserbao.db");
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }


    public DaoSession getDaoSession() {
        return daoSession;
    }
}
