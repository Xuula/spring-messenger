function apiCall(addr, method, data){
    var res;
    $.ajax({url:addr, method:method, data:data, dataType: "json", async:false}).done(gotData => res = gotData);
    return res;
}

const API = {
    users: {
        get(user_id){ return apiCall('/users/get', 'GET', {id: user_id}) },
        search(str) { return apiCall('/users/search', 'GET', {partial_nick: str})}
    },
    auth: {
        currentuser(session_id){ return apiCall('/currentuser', 'GET', {session_id: session_id}) }
    },
    messages: {
        dialog_before(session_id, other_id, max_num, before=undefined){
            console.log(session_id, other_id, max_num, before);
            var data = {session_id:session_id, companion: other_id, max_num: max_num};
            if(before)
                data.before = before;
            return apiCall('/messages/get_before', 'GET', data);
        },
        dialog_after(session_id, other_id, after, max_num){
            return apiCall('/messages/get_after', 'GET', {session_id: session_id, companion: other_id,
                after: after, max_num: max_num});
        },
        send(session_id, other_id, text){
            return apiCall('/messages/send', 'POST', {session_id: session_id, companion: other_id, text: text});
        }
    }
}