let { createApp } = Vue;

createApp({
    data() {
        return {
            inputPassword: "",
            inputEmail: "",
        };
    },
    created() {

    },
    methods: {
        validation(email, password) {
            if (email == "" || password == "") {
                alert("Debe llenar todos los campos");
            } else {
                this.postClient(email, password);
            }
        },
        postClient(email, password) {
            axios
                .post("/app/login", {
                    password: password,
                    email: email,
                })
                .then((response) => {
                    this.clearData();
                    location.href = "http://localhost:8080/Web/Pages/accounts"
                })
                .catch((err) => console.log(err));
        },
        clearData() {
            this.inputPassword = "";
            this.inputEmail = "";
        },
    },
}).mount('#app');