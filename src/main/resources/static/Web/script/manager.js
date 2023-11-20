let { createApp } = Vue;

createApp({
    data() {
        return {
            allData: {},
            clients: [],
            loans: [],
            inputname: "",
            inputlastname: "",
            inputemail: "",
            inputpassword: ""
        };
    },
    created() {
        this.loadData();
        this.getLoans();
    },
    methods: {
        loadData() {
            axios
                .get("/api/clients")
                .then((response) => {
                    console.log(response.data)
                    this.allData = response.data;
                    this.clients = response.data;
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
        postClient(name, lastName, email, password) {
            console.log(name, lastName, email, password)
            axios
                .post("/api/clients", `name=${name}&lastName=${lastName}&email=${email}&password=${password}`)
                .then((response) => {
                    console.log(response.data)
                    this.loadData();
                    this.clearData();
                })
                .catch((err) => console.log(err));
        },
        clearData() {
            this.inputname = "";
            this.inputlastname = "";
            this.inputemail = "";
            this.inputpassword = "";
        },

        getLoans() {
            axios.get("/api/loans")
                .then((response) => {
                    this.loans = response.data
                }).catch((err) => console.log(err));
        },
        newLoan(name, maxAmount, payments, interes) {
            console.log(name, maxAmount, payments, interes)
            axios.post("/api/newloan", `name=${name}&maxAmount=${maxAmount}&payments=${payments}&interes=${interes}`)
                .then((response) => {
                    this.getLoans()
                }).catch((err) => console.log(err));
        },


    },
    computed: {

    }
}).mount('#app');