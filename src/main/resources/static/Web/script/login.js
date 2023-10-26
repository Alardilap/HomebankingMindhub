let { createApp } = Vue;

createApp({
    data() {
        return {
            inputName: "",
            inputLastName: "",
            inputPassword: "",
            inputEmail: "",
            loginEmail: "",
            loginPassword: "",
        };
    },
    created() {

    },
    methods: {
        register() {
            console.log(this.inputEmail, this.inputName)
            axios.post("/api/clients",
                `name=${this.inputName}&lastName=${this.inputLastName}&email=${this.inputEmail}&password=${this.inputPassword}`
            )
                .then((response) => {
                    axios.post("/api/login", `email=${this.inputEmail}&password=${this.inputPassword}`)
                        .then((response) => {
                            this.createAccount()
                            location.href = "Web/Pages/accounts.html"
                        }).catch((err) => console.log(err))
                })
                .catch((err) => console.log(err));
        },
        clearData() {
            this.inputName = "",
                this.inputLastName = "",
                this.inputPassword = "";
            this.inputEmail = "";
        },
        login() {
            axios.post("/api/login", `email=${this.loginEmail}&password=${this.loginPassword}`)
                .then((response) => {
                    Swal.fire({
                        position: 'center',
                        icon: 'success',
                        iconColor: 'grey',
                        title: 'Login Ok ',
                        showConfirmButton: false,
                        timer: 900
                    })
                    setTimeout(() => {
                        location.href = "/Web/Pages/accounts.html"
                    }, 1700)

                }).catch((err) => console.log(err))
        },
        signOut() {
            axios.post("/api/logout")
                .then((response) => {
                    Swal.fire({
                        position: 'center',
                        icon: 'success',
                        iconColor: 'grey',
                        title: 'Logout ok',
                        showConfirmButton: false,
                        timer: 900
                    })
                    setTimeout(() => {
                        location.href = "/index.html"
                    }, 1700)
                }).catch((err) => {
                })
        }, createAccount() {
            axios.post("/api/clients/current/accounts")
                .then((response) => {
                    console.log(response)
                }).catch((err) => console.log(err))
        }
    },
}).mount('#app-login');