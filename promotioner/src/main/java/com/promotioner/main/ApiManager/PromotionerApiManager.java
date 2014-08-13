package com.promotioner.main.ApiManager;
import com.promotioner.main.Model.PMessageBackData;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.concurrency.Schedulers;
import rx.subscriptions.Subscriptions;

/**
 * Created by chen on 14-7-19.
 */
public class PromotionerApiManager extends MainApiManager{

    private static final PromotionerApiInterface.ApiManagerNoticeService apiManager = restAdapter.create(PromotionerApiInterface.ApiManagerNoticeService.class);
    public static Observable<PMessageBackData> getPMessageBackData(final String channel_id, final String push_id, final String mac_address) {
        return Observable.create(new Observable.OnSubscribeFunc<PMessageBackData>() {
            @Override
            public Subscription onSubscribe(Observer<? super PMessageBackData> observer) {
                try {
                    observer.onNext(apiManager.getPMessageBackData(channel_id,push_id,mac_address));
                    observer.onCompleted();
                } catch (Exception e) {
                    observer.onError(e);
                }

                return Subscriptions.empty();
            }
        }).subscribeOn(Schedulers.threadPoolForIO());
    }
}
