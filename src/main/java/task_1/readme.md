## Task 1: Make a request to LLM with predefined JSON response format
With a request to LLM you are eligible to provide template of expected response in JSON format.

Documentation: https://platform.openai.com/docs/guides/text-generation?text-generation-quickstart-example=json

<details> 
<summary>Request sample</summary>


```markdown
curl https://api.openai.com/v1/chat/completions \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $OPENAI_API_KEY" \
  -d '{
    "model": "gpt-4o-2024-08-06",
    "messages": [
      {
        "role": "developer",
        "content": "You extract email addresses into JSON data."
      },
      {
        "role": "user",
        "content": "Feeling stuck? Send a message to help@mycompany.com."
      }
    ],
    "response_format": {
      "type": "json_schema",
      "json_schema": {
        "name": "email_schema",
        "schema": {
            "type": "object",
            "properties": {
                "email": {
                    "description": "The email address that appears in the input",
                    "type": "string"
                }
            },
            "additionalProperties": false
        }
      }
    }
  }'
```
</details> 

<details> 
<summary>LLM Response sample</summary>

```json
{
  "id": "chatcmpl-AopkAdFosCsN9QewKWIIjnCEm5gme",
  "object": "chat.completion",
  "created": 1736678666,
  "model": "gpt-4o-2024-08-06",
  "choices": [
    {
      "index": 0,
      "message": {
        "role": "assistant",
        "content": "{\"persons\":[{\"name\":\"John\",\"surname\":\"Smith\",\"age\":34,\"hobbies\":[\"Fishing\",\"woodworking\",\"hiking\"]},{\"name\":\"Emily\",\"surname\":\"Peterson\",\"age\":27,\"hobbies\":[\"Photography\",\"painting\",\"volunteering at animal shelters\"]},{\"name\":\"Michael\",\"surname\":\"Johnson\",\"age\":42,\"hobbies\":[\"Gardening\",\"playing chess\",\"marathon running\"]},{\"name\":\"Sophia\",\"surname\":\"Hernandez\",\"age\":29,\"hobbies\":[\"Baking\",\"yoga\",\"blogging about travel\"]},{\"name\":\"David\",\"surname\":\"Lee\",\"age\":36,\"hobbies\":[\"Reading\",\"cycling\",\"bird watching\"]}]}",
        "refusal": null
      },
      "logprobs": null,
      "finish_reason": "stop"
    }
  ],
  "usage": {
    "prompt_tokens": 472,
    "completion_tokens": 137,
    "total_tokens": 609,
    "prompt_tokens_details": {
      "cached_tokens": 0,
      "audio_tokens": 0
    },
    "completion_tokens_details": {
      "reasoning_tokens": 0,
      "audio_tokens": 0,
      "accepted_prediction_tokens": 0,
      "rejected_prediction_tokens": 0
    }
  },
  "service_tier": "default",
  "system_fingerprint": "fp_703d4ff298"
}

```
</details>

## Pre Steps:
🔑 Add to the project Open-AI API key https://platform.openai.com/settings/organization/api-keys (It is not Free, you
   need to pay 1-10$ for subscription)



### Steps
1. Create request with response format
2. Call LLM `https://api.openai.com/v1/chat/completions`. Don't forget that content type is `application/json`
3. Get response and convert `content` to `Users` object with `ObjectMapper`
4. Validate with tests `task_1.TextToJsonTest`. 
5. **PAY ATTENTION THAT TESTS PROVIDE ONLY END-TO-END SCENARIO, TO CHECK THAT RESPONSE IS CONVERTABLE TO USERS OBJECT**