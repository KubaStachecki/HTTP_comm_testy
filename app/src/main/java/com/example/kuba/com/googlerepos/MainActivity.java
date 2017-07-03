package com.example.kuba.com.googlerepos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.kuba.com.googlerepos.component.DaggerServiceComponent;
import com.example.kuba.com.googlerepos.component.ServiceComponent;
import com.example.kuba.com.googlerepos.modules.ServiceModule;
import com.example.kuba.com.googlerepos.service.ReposService;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.list_view_repos)
    ListView listView;
    private ReposService.ReposApi reposApi;
    ServiceComponent serviceComponent;

    @Inject
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }
    private void injectServiceComponent(){
        String url = "https://api.github.com/users";
        serviceComponent = DaggerServiceComponent.builder()
                .serviceModule(new ServiceModule(url))
                .build();
        serviceComponent.inject(this);
        reposApi = retrofit.create(ReposService.ReposApi.class);
    }
//    https://api.github.com/users

}
