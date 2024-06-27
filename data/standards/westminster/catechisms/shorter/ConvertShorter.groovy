import groovy.json.JsonSlurper
import groovy.yaml.YamlSlurper
import java.nio.file.Path
import java.io.File
import groovy.json.JsonBuilder
import groovy.json.JsonGenerator
 
Object initialize() {
    println "groovy ver " + GroovySystem.version

}
 
void main() {
    initialize()

    def scriptDir = new File(getClass().protectionDomain.codeSource.location.path).parent
    File shorterFile = new File("${scriptDir}/shorterNew.json")
    
    println "*********** Reading ${shorterFile} ***********"
    File output1 = new File("${scriptDir}/shorterNewConverted.js")
    output1.createNewFile()
    readShorter(shorterFile, output1)
 
}

class Catechism {
  Integer id
  String question
  String[] answer
  ProofText[][] proofTexts
}

class ProofText {
  String book
  String verse
  String[] text
}

void readShorter(def fromFile, def outputFile) {
    //def writer = outputFile.newWriter('utf-8')
    //def builder = new groovy.json.JsonBuilder(writer)
    /* def shorter = builder.shorter {
        content 'test'
    } */
    //builder.put('items', [])
    Map fromJsonObj = new JsonSlurper().parseText(fromFile.getText())
    //def outputMap = [name: tool_name , product: product_name , platform: platform_name]

    def shorterItems = []
    fromJsonObj.shorter.each {obj ->
        { 
            def proofs = []
            obj.proofTexts.each { proofKey, proofValue -> 
                {
                    def proofRefs = []
                    proofValue.each { ref -> 
                    { 
                        //def pattern = /^(.*?)\s*\d+:\d+/
                        //def book = pattern.matcher(ref)
                        def splitRef = ref.split(/ (?!.* )/)
                        def book = splitRef[0]
                        def verse = splitRef[1]
                        def newProofText = new ProofText()
                        //newProofText.book = book
                        //newProofText.verse = verse
                        newProofText.book = ref
                        newProofText.verse = ""
                        newProofText.text = []
                        proofRefs.add(newProofText)
                    }
                    }
                    
                    //newProofText.book = proofValue
                    proofs.add(proofRefs)
                }
            }
            def rawAnswer = obj.answer
            
            //def lastProof = rawAnswer.lastIndexOf("[")
            def answerLines = rawAnswer.split(/\[\d\]/)

            def item = new Catechism()
            item.id = obj.id
            item.question = obj.question
            item.answer = answerLines
            item.proofTexts = proofs
            shorterItems.add(item)
            println "${obj.id} = ${obj.question}"
            println obj.answer
            println obj.proofTexts
            
        }
    }

    def generator = new JsonGenerator.Options().build()
    def result = generator.toJson(shorterItems)

   def writer = outputFile.newWriter('utf-8')
   writer.write(result)
   writer.close()

}
 
main()