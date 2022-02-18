class EasyHTTP {
    async put(url, data) {
        const response = await fetch(url, {
            method: 'PUT',
            headers: {
                'Content-type': 'text/html; charset=utf-8'
            },
            body: data
        });
        const resData = await response.json();
        return resData;
    }
}