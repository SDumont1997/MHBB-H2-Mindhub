const app = Vue.createApp({
    data(){
        return {
            client: {},
            creditCards: [],
            debitCards: [],
            accounts: [],
            cardType: "Card Type",
            cardColor: "Card Color",
            associatedAccount: "",
            cardCreationErrorMessage: "",
            cardNumber: "",
            password: "",
            cardDeletionError: ""
        }
    },
    created(){
        this.loadData()
    },
    methods: {
        loadData(){
            axios.get("/api/clients/current")
            .then(response => {
                this.client = response.data
                this.creditCards = this.sortById(response.data.cards).filter(card => card.cardType === "CREDIT")
                this.debitCards = this.sortById(response.data.cards).filter(card => card.cardType === "DEBIT")
                this.accounts = this.sortById(response.data.accounts)
            })
            .catch(e => {
                console.log(e)
            })
        },
        sortById(array){
            array.sort((elementA, elementB) => {
                if(elementA.id < elementB.id){
                    return -1
                }
                if(elementA.id > elementB.id){
                    return 1
                }
                return 0
            })
            return array
        },
        logOut(){
            axios.post("/api/logout")
            .then(response => {
                window.location.replace("/web/index.html")
            })
        },
        createCard(){
            axios.post("/api/clients/current/cards", `cardType=${this.cardType}&cardColor=${this.cardColor}&accountNumber=${this.associatedAccount}`)
            .then(response => {
                window.location.reload()
            })
            .catch(error => {
                this.cardCreationErrorMessage = error.response.data
            })
        },
        setErasedCard(event){
            this.cardNumber = event.target.value
        },
        deleteCard(){
            axios.post("/api/clients/current/cards/delete", `cardNumber=${this.cardNumber}&password=${this.password}`)
            .then(response=> window.location.reload())
            .catch(error => this.cardDeletionError = error.response.data)
        }
    },
    computed: {
        isDisabled(){
            if(this.cardType === "Card Type" || this.cardColor === "Card Color" || this.associatedAccount === ""){
                return true
            }
            return false
        }
    }
})

app.mount("#app")