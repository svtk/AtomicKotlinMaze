package game

import org.junit.Test

class TestMonster : AbstractTestGame() {
    @Test
    fun test1() {
        checkOptions(
            listOf(Move.WAIT),
            """
            #   R#
            # M###
            #    #""",
            setOf(
                """
            # M R#
            #  ###
            #    #""",
                """
            #   R#
            #M ###
            #    #""",
                """
            #   R#
            #  ###
            # M  #"""
            )
        )
    }

    @Test
    fun test2() {
        checkOptions(
            listOf(Move.WAIT),
            """
            ######
            # M2##
            ######""",
            setOf(
                """
            ### ##
            #    #
            ### ##""",
                """
            ######
            #M 2##
            ######"""
            )
        )
    }
}