let { createApp } = Vue;

createApp({
    data() {
        return {
            allData: {},
            clients: [],
            inputname: "",
            inputlastname: "",
            inputemail: "",
        };
    },
    created() {
        this.loadData();
    },
    methods: {
        loadData() {
            axios
                .get("/clients")
                .then((response) => {
                    console.log(response.data)
                    this.allData = response.data;
                    this.clients = response.data._embedded.clients;
                })
                .catch((err) => console.log(err));
        },
        addClient(name, lastName, email) {
            if (name == "" || lastName == "" || email == "") {
                alert("Debe llenar todos los campos");
            } else {
                this.postClient(name, lastName, email);
            }
        },
        postClient(name, lastName, email) {
            axios
                .post("/clients", {
                    name: name,
                    lastName: lastName,
                    email: email,
                })
                .then((response) => {
                    this.loadData();
                    this.clearData();
                })
                .catch((err) => console.log(err));
        },
        clearData() {
            this.inputname = "";
            this.inputlastname = "";
            this.inputemail = "";
        },
    },
}).mount('#app');