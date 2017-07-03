package com.example.kuba.com.googlerepos;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.kuba.com.googlerepos.component.DaggerServiceComponent;
import com.example.kuba.com.googlerepos.component.ServiceComponent;
import com.example.kuba.com.googlerepos.model.Repos;
import com.example.kuba.com.googlerepos.modules.ServiceModule;
import com.example.kuba.com.googlerepos.service.ReposService;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.list_view_repos)
    ListView listView;
    private ReposService.ReposApi reposApi;
    private List<String> reposNames = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapter;
    ServiceComponent serviceComponent;

    @Inject
    Retrofit retrofit;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        configureListView();
        injectServiceComponent();
        fetchRepos();


    }

    private void injectServiceComponent() {
        String url = "https://api.github.com/users/";
        serviceComponent = DaggerServiceComponent.builder()
                .serviceModule(new ServiceModule(url))
                .build();
        serviceComponent.inject(this);
        reposApi = retrofit.create(ReposService.ReposApi.class);
    }


    private void fetchRepos() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        reposApi.fetchRepos()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Repos>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull List<Repos> reposes) {

                        for (int i = 0; i < reposes.size() - 1; i++) {

                            reposNames.add(reposes.get(i).getName());
                        }

                        arrayAdapter.notifyDataSetChanged();

                        Toast.makeText(MainActivity.this, "PROCESSS", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("LOOOOOOG ", e.getMessage());

                        Toast.makeText(MainActivity.this, "FAAAAIL" + e.getMessage(), Toast.LENGTH_SHORT).show();

                        progressDialog.dismiss();

                    }

                    @Override
                    public void onComplete() {

                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this, "SUKCES", Toast.LENGTH_SHORT).show();

                    }


                });
    }

    private void configureListView() {

        arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.list_view_black_text, R.id.list_content, reposNames);

        listView.setAdapter(arrayAdapter);
        listView.invalidateViews();


    }


}
