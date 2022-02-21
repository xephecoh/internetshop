let form = document.getElementById("user-form");
let id = document.getElementById("id").value;
let name = document.getElementById("name").value;
let price = document.getElementById("price").value;
let data = {id: id, name: name, price: price}

form.onsubmit( () =>
    {
        post("http://localhost:8081/update", data)
            .then(response => console.log(response))
            .then(() => window.location.assign("http://localhost:8081/products"))
            .catch(error => console.error(error));
    }
)

function post(url, data) {
    return new Promise((succeed, fail) => {
        const xhr = new XMLHttpRequest();
        xhr.open("PUT", url);
        xhr.addEventListener("load", () => {
            if (xhr.status >= 200 && xhr.status < 400)
                succeed(xhr.response);
            else
                fail(new Error(`Request failed: ${xhr.statusText}`));
        });
        xhr.addEventListener("error", () => fail(new Error("Network error")));
        xhr.send(data);
    });
}


