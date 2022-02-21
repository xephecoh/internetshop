let form = document.getElementById("user-form");
let id = document.getElementById("id").value;
let name = document.getElementById("name").value;
let price = document.getElementById("price").value;
form.addEventListener("submit", takeValue)

{
    function takeValue() {
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
            // .then(window.location.assign('/'))
            .catch(err => console.log(err));
    }
}
