const app = Vue.createApp({
    data(){
        return {
            formEmail: "",
            formPassword: "",
            logInErrorMessage: "",
            signUpFirstName: "",
            signUpLastName: "",
            signUpEmail: "",
            signUpPassword: "",
            signUpErrorMessage: "",
            loggedIn: false,
            currencyList: [],
            currencyAmount: 1,
            convertFrom: "USD",
            convertTo: "USD",
            exchangeRate : 0,
            calculatorUpperBody: {
                from: "",
                to: "",
                amount: 0,
                rate: 0,
            },
            client: {}
        }
    },
    created(){
        this.checkActiveUser()
        this.getCurrencyData()
    },
    methods: {
        logIn(){
            axios.post("/api/login", `email=${this.formEmail}&password=${this.formPassword}`, {headers:{"content-type":"application/x-www-form-urlencoded"}})
            .then(response => {
                window.location.reload()   
            })
            .catch(error => {
                if(error.response.status === 409){
                    this.logInErrorMessage = "User already has an ongoing session"
                } else {
                    this.logInErrorMessage = "Invalid username or password"
                }
            })
        },
        signUpLogIn(email, password){
            axios.post("/api/login", `email=${email}&password=${password}`, {headers:{"content-type":"application/x-www-form-urlencoded"}})
            .then(response => {
                window.location.reload()
            })
        },
        logOut(){
            axios.post("/api/logout")
            .then(response => {
                window.location.reload()
            })
        },
        checkActiveUser(){
            axios.get("/api/clients/current")
            .then(response => {
                this.client = response.data
                this.loggedIn = true
            })
            .catch(error => {
                console.log("No user currently active")
            })
        },
        signUp(){
            axios.post("/api/clients", `firstName=${this.signUpFirstName}&lastName=${this.signUpLastName}&email=${this.signUpEmail}&password=${this.signUpPassword}`, {headers:{'content-type':'application/x-www-form-urlencoded'}})
            .then(response => {
                this.signUpLogIn(this.signUpEmail, this.signUpPassword)
            })
            .catch(error => {
                this.signUpErrorMessage = error.response.data
            })
        },
        sortByName(currencyArray){
            currencyArray.sort((currencyA, currencyB) => {
                if(currencyA.currencyName < currencyB.currencyName){
                    return -1
                }
                if(currencyA.currencyName > currencyB.currencyName){
                    return 1
                }
                return 0
            })
            return currencyArray
        },
        getCurrencyData(){
            axios.get("https://free.currconv.com/api/v7/currencies?apiKey=5315fa24ff3312de678a")
            .then(response => {
                this.currencyList = this.sortByName(Object.values(response.data.results))
            })
            .catch(error => {
                console.error(error)
            })
        },
        getExchangeRate(){
            axios.get(`https://free.currconv.com/api/v7/convert?q=${this.convertFrom}_${this.convertTo}&compact=ultra&apiKey=5315fa24ff3312de678a`)
            .then(response => {
               this.exchangeRate = (Object.values(response.data)[0]*this.currencyAmount)
               this.calculatorUpperBody.from = this.convertFrom
               this.calculatorUpperBody.to = this.convertTo
               this.calculatorUpperBody.amount = this.currencyAmount
               this.calculatorUpperBody.rate = this.exchangeRate
            })
            .catch(error => {
                console.error(error)
            })
        },
        switchCurrencies(){
            let tempAmount = this.currencyAmount
            let tempConvertFrom = this.convertFrom
            let tempConvertTo = this.convertTo
            let tempExchangeRate = this.exchangeRate

            this.convertFrom = tempConvertTo
            this.convertTo = tempConvertFrom

            if(this.exchangeRate !== 0){
                this.currencyAmount = tempExchangeRate
                this.exchangeRate = tempAmount
            }
        }

    },
    computed: {
    }

})
app.mount("#app")