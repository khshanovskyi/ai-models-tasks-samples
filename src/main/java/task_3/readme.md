## Task 2: Image to Text. Make a request to LLM to extract description of image

Documentation: https://platform.openai.com/docs/guides/vision

<details> 
<summary>Request sample with Base64 picture</summary>

You need to pass Base64 representation of picture to url

```markdown
curl https://api.openai.com/v1/chat/completions \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $OPENAI_API_KEY" \
  -d '{
    "model": "gpt-4o-mini",
    "messages": [
      {
        "role": "user",
        "content": [
          {
            "type": "text",
            "text": "What is in this image?"
          },
          {
            "type": "image_url",
            "image_url": {
              "url": "data:image/jpeg;base64,<base64_image>"
            }
          }
        ]
      }
    ],
    "max_tokens": 300
  }'
```
</details> 

<details> 
<summary>Request sample with picture's URL</summary>

```markdown
curl https://api.openai.com/v1/chat/completions \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $OPENAI_API_KEY" \
  -d '{
    "model": "gpt-4o-mini",
    "messages": [
      {
        "role": "user",
        "content": [
          {
            "type": "text",
            "text": "What is in this image?"
          },
          {
            "type": "image_url",
            "image_url": {
              "url": "https://upload.wikimedia.org/wikipedia/commons/thumb/d/dd/Gfp-wisconsin-madison-the-nature-boardwalk.jpg/2560px-Gfp-wisconsin-madison-the-nature-boardwalk.jpg"
            }
          }
        ]
      }
    ],
    "max_tokens": 300
  }'
```
</details> 

<details> 
<summary>LLM Response sample</summary>

```json
{
   "id": "chatcmpl-AouwpCjlbyBiLcMjI49hTYoBV3aHR",
   "object": "chat.completion",
   "created": 1736698671,
   "model": "gpt-4o-2024-08-06",
   "choices": [
      {
         "index": 0,
         "message": {
            "role": "assistant",
            "content": "This image shows several folded T-shirts in plastic packaging. Each T-shirt has the word \"CODEUS\" printed on it, and they come in various colors like teal, white, beige, and dark gray. Some have tags labeled \"STAFF\" or \"bug.\"",
            "refusal": null
         },
         "logprobs": null,
         "finish_reason": "stop"
      }
   ],
   "usage": {
      "prompt_tokens": 874,
      "completion_tokens": 55,
      "total_tokens": 929,
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
   "system_fingerprint": "fp_b7d65f1a5b"
}
```
</details>

## Pre Steps:
🔑 Add to the project Open-AI API key https://platform.openai.com/settings/organization/api-keys (It is not Free, you
   need to pay 1-10$ for subscription)


### Steps
1. Create request with response format
2. Call LLM `https://api.openai.com/v1/chat/completions`. Don't forget that content type is `application/json`
3. Get response and get `content`
4. Validate with tests `task_2.ImageToTextTest`. 
5. **PAY ATTENTION THAT TESTS PROVIDE ONLY END-TO-END SCENARIO, TO CHECK THAT RESPONSE IS CONVERTABLE TO USERS OBJECT**