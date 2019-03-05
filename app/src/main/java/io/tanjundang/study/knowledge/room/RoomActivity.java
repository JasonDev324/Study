package io.tanjundang.study.knowledge.room;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.tanjundang.study.R;

/**
 * @Author: TanJunDang
 * @Date: 2019/3/5 11:06
 * @Description: Google ORM框架Room Demo
 */
public class RoomActivity extends AppCompatActivity {

    @BindView(R.id.btnInsert)
    Button btnInsert;
    @BindView(R.id.loadData)
    Button loadData;
    @BindView(R.id.btnDelByKey)
    Button btnDelByKey;
    @BindView(R.id.btnDel)
    Button btnDel;
    @BindView(R.id.tvContent)
    TextView tvContent;

    UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        ButterKnife.bind(this);
        AppDataBase db = Room.databaseBuilder(getApplicationContext(),
                AppDataBase.class, "roomDemo-database.db")
                //下面注释表示允许主线程进行数据库操作，但是不推荐这样做。
                //他可能造成主线程lock以及anr
                //所以我们的操作都是在新线程完成的
                // .allowMainThreadQueries()
                .build();
        userDao = db.getUserDao();
    }

    int count = 0;

    @OnClick({R.id.btnInsert, R.id.loadData, R.id.btnDelByKey, R.id.btnDel})
    public void onViewClicked(final View view) {
        switch (view.getId()) {
            case R.id.btnInsert:
                Observable
                        .create(new ObservableOnSubscribe<Object>() {
                            @Override
                            public void subscribe(ObservableEmitter<Object> e) throws Exception {
                                e.onNext(1);
                            }
                        })
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Consumer<Object>() {
                            @Override
                            public void accept(Object o) throws Exception {
                                User user = new User();
                                user.setName("wmz" + count);
                                userDao.insert(user);
                                count++;
                            }
                        });
                break;
            case R.id.loadData:
                Observable.just(1)
                        .subscribeOn(Schedulers.io())
                        .map(new Function<Integer, List<User>>() {
                            @Override
                            public List<User> apply(Integer integer) throws Exception {
                                List<User> users = userDao.getUserList();
                                return users;
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<List<User>>() {
                            @Override
                            public void accept(List<User> users) throws Exception {
                                tvContent.setText(null);
                                for (int i = 0; i < users.size(); i++) {
                                    tvContent.append("name:" + users.get(i).getName() + "    id:" + users.get(i).getUid() + "\n");
                                }
                            }
                        });


                break;
            case R.id.btnDelByKey:
                Observable.just(1)
                        .subscribeOn(Schedulers.io())
                        .doOnNext(new Consumer<Object>() {
                            @Override
                            public void accept(Object o) throws Exception {
                                User userDel = new User();
                                userDel.setUid(count);
                                userDao.delete(userDel);
                                count--;
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Object>() {
                            @Override
                            public void accept(Object o) throws Exception {
                                Snackbar.make(view, "删除当前数据成功", Snackbar.LENGTH_LONG).show();
                            }
                        });

                break;
            case R.id.btnDel:
                Observable.just(1)
                        .observeOn(Schedulers.io())
                        .map(new Function<Object, Integer>() {
                            @Override
                            public Integer apply(Object o) throws Exception {
                                int delSize = userDao.clear();
                                return delSize;
                            }
                        })
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Integer>() {
                            @Override
                            public void accept(Integer o) throws Exception {
                                Snackbar.make(view, "删除数据成功" + "delSize:" + o, Snackbar.LENGTH_LONG).show();
                            }
                        });
                break;
        }
    }
}
