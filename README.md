# UserArrivalGenerator
進行實驗前以機率統計方式得知長期試驗中有多少比例的使用者會符合使用者願意容忍時間(tolerance time)。

進行實驗時使用者的 arrival 有時候來太快會導致VM機器數量縮放規則(scaling rule)來不及反應使用者行為。
造成化大量時間在產生實驗資料(data set)，只要實驗資料沒有產生好就會導致實驗失敗，而且這種失敗是無法預先知道的。
