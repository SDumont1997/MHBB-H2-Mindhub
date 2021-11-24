const app = Vue.createApp({
    data(){
        return{
            json: {},
            clients: [],
            firstName: "",
            lastName: "",
            email: "",
        }
    },
    created(){
        this.loadData();
    },
    methods: {
        loadData(){
            axios.get('/rest/clients')
            .then(response =>{
                this.json = response.data;
                this.clients = response.data._embedded.clients;
            })
            .catch(e => {
                console.log(e);
            })
        },
        addClient(){
            let client = {
                firstName: this.firstName,
                lastName: this.lastName,
                email: this.email
            }
            this.postClient(client)
        },
        postClient(client){
            axios.post('/rest/clients', client)
            .then(response => {
                this.firstName = ""
                this.lastName = ""
                this.email = ""
                this.loadData()
            })
            .catch(e => {
                console.log(e);
            })
        },
        deleteClient(e){
            axios.delete(e.target.value)
            .then(response => {
                this.loadData()
            })
            .catch(e => {
                console.log(e)
            })
        }

    },
    computed: {
        isDisabled(){
            if(this.firstName !== "" && this.lastName !== "" && this.email !== ""){
                return false;
            } else {
                return true;
            }
        }
    }
    

})
app.mount("#app")