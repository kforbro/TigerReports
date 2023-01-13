package fr.mrtigreroux.tigerreports.tasks;

@FunctionalInterface
public interface ResultCallback<R> {

    ResultCallback<Integer> NOTHING = new ResultCallback<Integer>() {

        @Override
        public void onResultReceived(Integer r) {
            // Nothing
        }

    };

    void onResultReceived(R r);

}
