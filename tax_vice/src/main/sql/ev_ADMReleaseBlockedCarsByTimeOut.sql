drop event if exists `ev_ADMReleaseBlockedCarsByTimeOut`;

create event if not exists `ev_ADMReleaseBlockedCarsByTimeOut`
on schedule every 10 second
on completion preserve enable
do
   
	call sp_ADMClearUncommitedOrders();