package com.salon.cattocdi.fragements;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.salon.cattocdi.R;
import com.salon.cattocdi.adapters.CommentAdapter;
import com.salon.cattocdi.models.Comment;
import com.salon.cattocdi.models.Salon;
import com.salon.cattocdi.utils.MyContants;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * <p>
 * to handle interaction events.
 */
public class ReviewsFragment extends Fragment {

    private Salon salon;
    private RatingBar tvRatingNumber;
    private TextView tvTotalReviews;
    private RecyclerView rvComments;


    public ReviewsFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public ReviewsFragment(Salon salon) {
        this.salon = salon;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reviews, container, false);

        tvRatingNumber = view.findViewById(R.id.fg_reviews_sum);

        tvTotalReviews = view.findViewById(R.id.salon_detail_total_reviews_tv);

        rvComments = view.findViewById(R.id.salon_detail_comment_rv);
        ViewCompat.setNestedScrollingEnabled(rvComments, false);
        rvComments.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rvComments.setAdapter(new CommentAdapter(salon.getReviews()));

        setProgressBar(view);

        return view;
    }

    private void setProgressBar(View view) {

        ProgressBar pb1, pb2, pb3, pb4, pb5;
        TextView tv1, tv2, tv3, tv4, tv5;
        pb1 = view.findViewById(R.id.salon_detail_review_1_pb);
        tv1 = view.findViewById(R.id.salon_detail_review_1_tv);
        pb2 = view.findViewById(R.id.salon_detail_review_2_pb);
        tv2 = view.findViewById(R.id.salon_detail_review_2_tv);
        pb3 = view.findViewById(R.id.salon_detail_review_3_pb);
        tv3 = view.findViewById(R.id.salon_detail_review_3_tv);
        pb4 = view.findViewById(R.id.salon_detail_review_4_pb);
        tv4 = view.findViewById(R.id.salon_detail_review_4_tv);
        pb5 = view.findViewById(R.id.salon_detail_review_5_pb);
        tv5 = view.findViewById(R.id.salon_detail_review_5_tv);

        int count1 = 0, count2 = 0, count3 = 0, count4 = 0, count5 = 0;
        if (salon.getReviews() != null && salon.getReviews().size() > 0) {
            for (Comment com :
                    salon.getReviews()) {
                switch (com.getRating()) {
                    case 1:
                        count1++;
                        break;
                    case 2:
                        count2++;
                        break;
                    case 3:
                        count3++;
                        break;
                    case 4:
                        count4++;
                        break;
                    case 5:
                        count5++;
                        break;
                }
            }

            pb1.setProgress(Math.round((float) count1 / salon.getReviews().size())*100);
            pb2.setProgress(Math.round((float) count2 / salon.getReviews().size())*100);
            pb3.setProgress(Math.round((float) count3 / salon.getReviews().size())*100);
            pb4.setProgress(Math.round((float) count4 / salon.getReviews().size())*100);
            pb5.setProgress(Math.round((float) count5 / salon.getReviews().size())*100);

            tv1.setText(Math.round((float) count1 / salon.getReviews().size()) + "");
            tv2.setText(Math.round((float) count2 / salon.getReviews().size()) + "");
            tv3.setText(Math.round((float) count3 / salon.getReviews().size()) + "");
            tv4.setText(Math.round((float) count4 / salon.getReviews().size()) + "");
            tv5.setText(Math.round((float) count5 / salon.getReviews().size()) + "");

            float avgRating = (count1 + count2 * 2 + count3 * 3 + count4 * 4 + count5 * 5) / (count1 + count2 + count3 + count4 + count5);
            tvRatingNumber.setRating(avgRating);
            tvTotalReviews.setText(salon.getReviews().size() + " Đánh giá");
        }


    }

}
