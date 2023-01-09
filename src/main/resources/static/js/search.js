// $("search").on("click", function (event){
//     event.preventDefault();
//     var formData = $("seachInput").value;
//     console.log(formData);
//     $("#search_result").html('')
//     $.post({
//         url:"http://localhost:8080/search",
//         data:formData.stringify,
//         dataType: "json",
//         success: function (data){
//             console.log(data);
//             $("#search_result").append('' +
//     '              <div class="basic_style-infor">\n' +
//     '                <div class="search_result-detail">Member ID:</div>\n' +
//     '                <div class="information_box-infor" th:text="${confirmObjectDTO.account.member.memberID}"></div>\n' +
//     '              </div>\n' +
//     '              <div class="basic_style-infor">\n' +
//     '                <div class="search_result-detail">Identity Card:</div>\n' +
//     '                <div class="information_box-infor" th:text="${confirmObjectDTO.account.identityCard}"></div>\n' +
//     '              </div>\n' +
//     '              <div class="basic_style-infor">\n' +
//     '                <div class="search_result-detail">Full name:</div>\n' +
//     '                <div class="information_box-infor" th:text="${confirmObjectDTO.account.fullName}"></div>\n' +
//     '              </div>\n' +
//     '              <div class="basic_style-infor">\n' +
//     '                <div class="search_result-detail">Phone</div>\n' +
//     '                <div class="information_box-infor" th:text="${confirmObjectDTO.account.phoneNumber}"></div>\n' +
//     '              </div>\n' +
//     '              <div class="basic_style-infor">\n' +
//     '                <div class="search_result-detail">Score</div>\n' +
//     '                <div class="information_box-infor score"  th:text="${confirmObjectDTO.account.member.score}"></div>\n' +
//     '              </div>\n' +
//     '              <div class="basic_style-infor">\n' +
//     '                <div class="search_result-detail">Convert to ticket:</div>\n' +
//     '                <form th:if="${confirmObjectDTO.account.member.score >= 10000}"\n' +
//     '                      class="information_box-infor"\n' +
//     '                      id="convert_Ticket"\n' +
//     '                      method="post"\n' +
//     '                      th:action="@{/showtime/{date}/{movieID}/{time}/confirm/{seatsSelec}/{customerID}/employee/ticketInfor(date=${dates.showDate}, movieID=${movie.movieId}, time=${schedule.id}, seatsSelec=${seatsSelec}, customerID=${account.accountID})}">>\n' +
//     '                  <input type="radio" id="agree" name="fav_language" value="Agree">\n' +
//     '                  <label for="agree">Agree</label><br>\n' +
//     '                  <input type="radio" id="disagree" name="fav_language" value="Disagree">\n' +
//     '                  <label for="disagree">Disagree</label><br>\n' +
//     '                </form>\n' +
//     '                <div  class="information_box-infor" th:if="${confirmObjectDTO.account.member.score < 10000}">\n' +
//     '                  <div class="alert alert-danger text-center ">Member score is not enough to convert into ticket (>10000)</div>\n' +
//     '                </div>\n' +
//     '              </div>' +
//                 '');
//         },
//         error: function(data){
//
//         }
//     });
// });