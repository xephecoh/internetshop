
document.forms["myFrom"].submit
{
    function takeValue() {
        let id = document.getElementById("id").value;
        let name = document.getElementById("name").value;
        let price = document.getElementById("price").value;
        const http = new EasyHTTP;
        const data = {
            name: name,
            id: id,
            price: price
        }
        http.put(
            'http://localhost:8081/update',
            data)
            .then(data => console.log(data))
            .catch(err => console.log(err));

    }
}