package nichanan.sunteen.co.th.sunteenfriend;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import nichanan.sunteen.co.th.sunteenfriend.fragment.ServiceFragment;

public class ServiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.contentServiceFragment, new ServiceFragment())
                    .commit();
        }
    }
}
