package com.example.smoot.ajerwaojra.Fragments;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.smoot.ajerwaojra.Models.Doer;
import com.example.smoot.ajerwaojra.R;
import com.example.smoot.ajerwaojra.Helpers.SharedPrefManager;

public class FatwaFragment extends Fragment {
    ImageButton doer ;
    ImageButton requester;
    Fragment doerFrag;
    Fragment requesterFrag;
    TextView advisoryOpinion;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_fatwa, container, false);
        doer = view.findViewById(R.id.doer);
        requester = view.findViewById(R.id.requester);
        advisoryOpinion = view.findViewById(R.id.advisoryOpinion);
        advisoryOpinion.setText("فإن الحج عن الميت والعمرة عن الميت من أفضل القربات، وينتفع بها الميت المسلم كثيرًا، فقد سئل النبي ﷺ عن ذلك مرات كثيرة فقال للسائل: حج عن أبيك، وللسائلة: حجي عن أبيك، والآخر: عن أمك، وسأله آخر قال: إني لبيت عن شبرمة قال: من شبرمة؟ قال: أخ لي أو قريب لي. قال: حج عن نفسك ثم حج عن شبرمة." +
                "فالناس أقسام منهم من قد حج الفريضة وأدى العمرة الفريضة هذا إذا حج عنه يكون نافلة، وإذا اعتمر عنه يكون نافلة، حج عنه أخوه أو أبوه أو قريب له أو أخ من إخوانه في الله كل ذلك طيب وهكذا العمرة، وإذا كان ما أدى الحج ولا أدى العمرة فإن الذي يحج عنه يكون قد أدى عنه الفريضة، وهكذا العمرة يكون قد أدى عنه عمرة الفريضة، وهو على كل حال مأجور والميت مأجور كلاهما مأجور، هذا عن عمله الطيب وإحسانه إلى أخيه مأجور والميت مأجور بذلك، وهكذا الصدقة وهكذا الدعاء إذا تصدق عن أخيه يؤجر هو والميت جميعًا، وهكذا إذا دعا لأخيه الميت يؤجر هو وينتفع الميت بالدعاء. نعم.");
         // if the user is logged in so go to home page
        // TODO : change the fragment from requester to doer
        if(SharedPrefManager.getInstance(getContext()).isLoggedIn()){
        //    SharedPrefManager.getInstance(getContext()).logout();
            Doer user = SharedPrefManager.getInstance(getContext()).getDoer();
           Fragment f = new logInFragment();
           android.support.v4.app.FragmentManager fm = getFragmentManager();
           FragmentTransaction ft = fm.beginTransaction();
           ft.replace(R.id.container, f);
           ft.commit();
        }
        // if the user is not logged in so go
       // else{ }



        doerFrag = new DoerRegistrationFragment();
        doer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doerFrag = new DoerRegistrationFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.container, doerFrag);
                ft.commit();
            }
        });

        requester.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requesterFrag = new RequesterRegistrationFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.container, requesterFrag);
                ft.commit();
            }
        });
        // Inflate the layout for this fragment
        return view;
    }}


