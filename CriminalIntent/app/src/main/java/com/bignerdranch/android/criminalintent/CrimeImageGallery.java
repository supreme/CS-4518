package com.bignerdranch.android.criminalintent;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CrimeImageGallery.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CrimeImageGallery#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CrimeImageGallery extends Fragment {
    private static final String CRIME_ID = "CRIME_ID";

    private OnFragmentInteractionListener mListener;

    private Crime crime;
    private GridView gridView;
    private List<CrimeImage> images;

    public CrimeImageGallery() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param crimeId The UUID of the crime to display the fragment for.
     * @return A new instance of fragment CrimeImageGallery.
     */
    public static CrimeImageGallery newInstance(UUID crimeId) {
        CrimeImageGallery fragment = new CrimeImageGallery();
        Bundle args = new Bundle();
        args.putSerializable(CRIME_ID, crimeId);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            crime = CrimeLab.get(getActivity()).getCrime((UUID) getArguments().getSerializable(CRIME_ID));
        }

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_image_gallery, container, false);

        images = CrimeLab.get(getActivity()).getCrimeImages(crime);
        List<Bitmap> bitmaps = new ArrayList<>();
        for (CrimeImage image : images) {
            File imageFile = CrimeLab.get(getActivity()).getCrimeImageFile(image);
            bitmaps.add(PictureUtils.getScaledBitmap(imageFile.getPath(), getActivity()));
        }

        gridView = view.findViewById(R.id.gridView);
        gridView.setAdapter(new ImageAdapter(getActivity(), bitmaps));
        images = CrimeLab.get(getActivity()).getCrimeImages(crime);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Toast.makeText(getActivity(), "" + position, Toast.LENGTH_SHORT).show();
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
