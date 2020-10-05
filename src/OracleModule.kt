package io.arnonuem

import io.ktor.application.*
import io.ktor.html.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.html.*
import kotlinx.html.dom.document

fun Application.oracle() {


    routing {
        get("/sfco") {
            call.combatPage()
        }

        route("/result") {
            get {
                call.respondText { "Run a calculation first! -> /sfco" }
            }
            post {
                val postData = call.receiveParameters()
                val myLife = postData["myLife"]
                val myDamage = postData["myDamage"]
                val enemyLife = postData["enemyLife"]
                val enemyDamage = postData["enemyDamage"]

                if ( myLife != null && myLife.isNotBlank() &&
                     myDamage != null && myDamage.isNotBlank() &&
                     enemyLife != null && enemyLife.isNotBlank() &&
                     enemyDamage != null && enemyDamage.isNotBlank() ) {
                    val result = CombatService.simulateFight(
                        Character(CharacterType.ME, myLife.toInt(), myDamage.toInt()),
                        Character(CharacterType.ENEMY, enemyLife.toInt(), enemyDamage.toInt())
                    )
                    call.respond(result)
                } else {
                    call.respondText("Invalid input!")
                }
            }
        }
    }
}


private suspend fun ApplicationCall.combatPage() {
    respondHtml {
        head {
            title { +"Shakes & Fidget Combat Oracle" }
            link {
                rel = "stylesheet"
                type = "text/css"
                href = "/static/style.css"
            }
        }
        body {
            form(action = "/result", method = FormMethod.post, encType = FormEncType.applicationXWwwFormUrlEncoded) {
                div(classes = "flex-container-centered mt-1") {
                    div(classes = "character character-me") {
                        h3 {
                            + "My Character"
                        }
                        div(classes = "form-group") {
                            label {
                                + "My Life"
                            }
                            input(classes = "input-control") {
                                name = "myLife"
                            }
                        }
                        div(classes = "form-group") {
                            label {
                                +"My Damage"
                            }
                            input(classes = "input-control") {
                                name = "myDamage"
                            }
                        }
                    }

                    div(classes = "character character-enemy") {
                        h3 {
                            + "The Enemy"
                        }
                        div(classes = "form-group") {
                            label {
                                + "Enemy Life"
                            }
                            input(classes = "input-control") {
                                name = "enemyLife"
                            }
                        }
                        div(classes = "form-group") {
                            label {
                                + "Enemy Damage"
                            }
                            input(classes = "input-control") {
                                name = "enemyDamage"
                            }
                        }
                    }
                }

                div(classes = "flex-container-centered") {
                    style = "padding: 1rem;"
                    input {
                        type = InputType.submit
                        name = "FIGHT!"
                        value = name
                    }
                }
            }
        }
    }
}
