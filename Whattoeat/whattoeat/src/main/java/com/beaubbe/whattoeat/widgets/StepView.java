package com.beaubbe.whattoeat.widgets;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.beaubbe.whattoeat.R;
import com.beaubbe.whattoeat.models.Step;

/**
 * Created by Gab on 08/02/14.
 */
public class StepView extends Fragment {
    private Step step;

    public StepView()
    {
    }

    public StepView(Step step, int stepNumber)
    {
        this.step = step;
        this.step.setStep(stepNumber);
    }
    public void setStep(Step step) {
        this.step = step;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.step_view_widget, container, false);

        ((TextView)root.findViewById(R.id.step_number)).setText(String.valueOf(step.getStep()));
        ((TextView)root.findViewById(R.id.duration)).setText(String.valueOf(step.getDurationAsString()));
        ((TextView)root.findViewById(R.id.description)).setText(step.getDescription());
        return root;
    }
}
